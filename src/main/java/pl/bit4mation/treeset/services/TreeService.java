package pl.bit4mation.treeset.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import pl.bit4mation.treeset.dao.TreeNodeRepository;
import pl.bit4mation.treeset.entities.TreeNode;

import java.util.List;

@Service
public class TreeService {

    @Autowired
    private TreeNodeRepository repository;

    public TreeNode create (TreeNode node) throws DataIntegrityViolationException {

        return repository.save(node);

    }

    public void update (TreeNode node) throws DataIntegrityViolationException {

        if (node.getId().equals(node.getParentId())) throw new DataIntegrityViolationException("Self reference not allowed!");
        repository.update(node);

    }

    public void delete (Long id) {

        repository.delete(id);

    }

    public List<TreeNode> get (Long parentId) {

        return repository.findByParentIdOrderById(parentId);

    }

    public Iterable<TreeNode> getAll () {

        return repository.findAll();

    }

}
