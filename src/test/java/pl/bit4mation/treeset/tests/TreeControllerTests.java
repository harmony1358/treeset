package pl.bit4mation.treeset.tests;

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
}
