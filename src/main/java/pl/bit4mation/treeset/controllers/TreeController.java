package pl.bit4mation.treeset.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@RestController
@RequestMapping("api")
public class TreeController {

    private static Logger LOG = LoggerFactory.getLogger(TreeController.class);

    @Autowired
    private TreeNodeRepository repository;

    @ApiOperation(value = "Create new TreeNode")
    @RequestMapping(method = RequestMethod.POST, value="/create")
    public ResponseEntity<TreeNode> create (@RequestBody TreeNode node) {

        try {

            return new ResponseEntity<TreeNode> (repository.save(node), HttpStatus.OK);
            
        } catch (DataIntegrityViolationException die) {

            return new ResponseEntity<TreeNode> (HttpStatus.FORBIDDEN);

        } 

    }

    @ApiOperation(value = "Update TreeNode contents")
    @RequestMapping(method = RequestMethod.POST, value="/update")
    public ResponseEntity<TreeNode> update (@RequestBody TreeNode node) {

        try {

            repository.update(node);
        
        } catch (DataIntegrityViolationException die) {

            return new ResponseEntity<TreeNode> (HttpStatus.FORBIDDEN);

        } 
        
        return new ResponseEntity<TreeNode> (node, HttpStatus.OK);

    }

    @ApiOperation(value = "Delete TreeNode and all its descendants")
    @RequestMapping(method = RequestMethod.POST, value="/delete/{id}")
    public void delete (@PathVariable Long id) {

        repository.delete(id);
    }

    @ApiOperation(value = "Get root TreeNodes")
    @RequestMapping(method = RequestMethod.GET, value="/get")
    public @ResponseBody List<TreeNode> getRoot () {

        return repository.findByParentIdOrderById(null);

    }

    @ApiOperation(value = "Get TreeNodes by parentId")
    @RequestMapping(method = RequestMethod.GET, value="/get/{parentId}")
    public @ResponseBody List<TreeNode> get (@PathVariable Long parentId) {

        return repository.findByParentIdOrderById(parentId);

    }

    @ApiOperation(value = "Get all TreeNodes, unstructured")
    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public @ResponseBody Iterable<TreeNode> getAll () {

        return repository.findAll();
    
    }
}
