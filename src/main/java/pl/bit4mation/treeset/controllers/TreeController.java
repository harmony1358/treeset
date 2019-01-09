package pl.bit4mation.treeset.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import pl.bit4mation.treeset.dao.TreeNodeRepository;
import pl.bit4mation.treeset.entities.TreeNode;
import pl.bit4mation.treeset.services.TreeService;

@RestController
@RequestMapping("api")
public class TreeController {

    @Autowired
    private TreeService treeService;

    @ApiOperation(value = "Create new TreeNode")
    @RequestMapping(method = RequestMethod.POST, value="/create")
    public ResponseEntity<TreeNode> create (@RequestBody TreeNode node) {

        try {

            return new ResponseEntity<TreeNode> (treeService.create(node), HttpStatus.OK);
            
        } catch (DataIntegrityViolationException die) {

            return new ResponseEntity<TreeNode> (HttpStatus.FORBIDDEN);

        } 

    }

    @ApiOperation(value = "Update TreeNode contents")
    @RequestMapping(method = RequestMethod.PUT, value="/update")
    public ResponseEntity<?> update (@RequestBody TreeNode node) {

        try {

            treeService.update(node);
        
        } catch (DataIntegrityViolationException die) {

            return new ResponseEntity<> (HttpStatus.FORBIDDEN);

        } 
        
        return new ResponseEntity<> (node, HttpStatus.OK);

    }

    @ApiOperation(value = "Delete TreeNode and all its descendants")
    @RequestMapping(method = RequestMethod.DELETE, value="/delete/{id}")
    public void delete (@PathVariable Long id) {

        treeService.delete(id);
    }

    @ApiOperation(value = "Get root TreeNodes")
    @RequestMapping(method = RequestMethod.GET, value="/get")
    public @ResponseBody List<TreeNode> getRoot () {

        return treeService.get(null);

    }

    @ApiOperation(value = "Get TreeNodes by parentId")
    @RequestMapping(method = RequestMethod.GET, value="/get/{parentId}")
    public @ResponseBody List<TreeNode> get (@PathVariable Long parentId) {

        return treeService.get(parentId);

    }

    @ApiOperation(value = "Get all TreeNodes, unstructured")
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public @ResponseBody Iterable<TreeNode> getAll () {

        return treeService.getAll();
    
    }
}
