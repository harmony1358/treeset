package pl.bit4mation.treeset.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.google.gson.Gson;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import pl.bit4mation.treeset.entities.TreeNode;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TreeControllerTests {

    private Gson gson = new Gson();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void get_should_return_empty_set_for_unknown_parent () throws Exception {

        this.mockMvc.perform(get("/api/get/777"))
            .andExpect(status().is(HttpStatus.OK_200))
            .andExpect(content().json("[]"));
    
    }

    @Test
    public void create_should_not_pass_with_orphaned_parent_id () throws Exception {

        TreeNode node = new TreeNode(10);
        node.setParentId(777l);
        
        this.mockMvc.perform(post("/api/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(node)))
            .andExpect(status().is(HttpStatus.FORBIDDEN_403));

    }

    @Test
    public void should_create_new_entity_and_return_object_with_id () throws Exception {

        TreeNode node = new TreeNode(10);
        node.setParentId(null);

        this.mockMvc.perform(post("/api/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(node)))
            .andExpect(status().is(HttpStatus.OK_200))
            .andExpect(jsonPath("$.id").isNumber());
    } 

    @Test
    public void should_delete_whole_branch_when_deleting_parent () throws Exception {

        TreeNode parent = new TreeNode(20);

        TreeNode node_210 = new TreeNode(210);
        TreeNode node_220 = new TreeNode(220);
        TreeNode node_230 = new TreeNode(230);

        parent = createNode(parent);

        node_210.setParentId(parent.getId());
        node_220.setParentId(parent.getId());
        node_230.setParentId(parent.getId());

        createNode (node_210);
        createNode (node_220);
        createNode (node_230);

        assertChildCount (parent, 3);

        this.mockMvc.perform(post("/api/delete/" + parent.getId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is(HttpStatus.OK_200));

        assertChildCount (parent, 0);
        
    }

    @Test
    public void should_not_pass_moving_with_orphaned_parent_id () throws Exception {

        TreeNode node = new TreeNode(30);

        node = createNode(node);
        node.setParentId(777l);

        this.mockMvc.perform(post("/api/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(node)))
            .andExpect(status().is(HttpStatus.FORBIDDEN_403));

    }

    @Test
    public void should_move_whole_branch_to_new_parent_id () throws Exception {

        TreeNode parent_1 = new TreeNode(40);
        TreeNode parent_2 = new TreeNode(40);
        TreeNode subParent = new TreeNode(41);

        TreeNode node_410 = new TreeNode(410);
        TreeNode node_420 = new TreeNode(420);
        TreeNode node_430 = new TreeNode(430);

        parent_1 = createNode (parent_1);
        parent_2 = createNode (parent_2);

        subParent.setParentId(parent_1.getId());
        subParent = createNode (subParent);

        node_410.setParentId(subParent.getId());
        node_420.setParentId(subParent.getId());
        node_430.setParentId(subParent.getId());

        createNode(node_410);
        createNode(node_420);
        createNode(node_430);

        assertChildCount (subParent, 3);
        assertChildCount (parent_1, 1);

        subParent.setParentId(parent_2.getId());

        this.mockMvc.perform(post("/api/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(subParent)))
            .andExpect(status().is(HttpStatus.OK_200));

        assertChildCount (subParent, 3);
        assertChildCount (parent_1, 0);
        assertChildCount (parent_2, 1);

    }

    @Test
    public void should_return_root_nodes () throws Exception {

        this.mockMvc.perform(get("/api/get"))
            .andExpect(status().is(HttpStatus.OK_200))
            .andExpect(jsonPath("$").isArray());

    }

    @Test
    public void should_return_all_nodes () throws Exception {

        this.mockMvc.perform(get("/api/all"))
            .andExpect(status().is(HttpStatus.OK_200))
            .andExpect(jsonPath("$").isArray());

    }

    @Test
    public void should_properly_set_parent_on_tree_node () throws Exception {

        TreeNode parent = new TreeNode(1);
        TreeNode child = new TreeNode(2);

        child.setParent(parent);

        assertEquals(child.getParent(), parent);

    }

    private TreeNode createNode (TreeNode node) throws Exception {

        this.mockMvc.perform(post("/api/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(node)))
            .andExpect(status().is(HttpStatus.OK_200))
            .andDo(mvcResult -> {
                TreeNode entity = gson.fromJson(mvcResult.getResponse().getContentAsString(), TreeNode.class);
                node.setId(entity.getId());
            });

        return node;
    }

    private void assertChildCount (TreeNode node, int length) throws Exception {

        this.mockMvc.perform(get("/api/get/" + node.getId()))
            .andExpect(status().is(HttpStatus.OK_200))
            .andDo(mvcResult -> {
                TreeNode[] nodes = gson.fromJson(mvcResult.getResponse().getContentAsString(), TreeNode[].class);
                assertTrue( (nodes.length == length) );
            });

    }
}
