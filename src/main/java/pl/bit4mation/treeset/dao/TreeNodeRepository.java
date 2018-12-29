package pl.bit4mation.treeset.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pl.bit4mation.treeset.entities.TreeNode;

public interface TreeNodeRepository extends CrudRepository<TreeNode, Long> {

    TreeNode findById (Long id);
    List<TreeNode> findByParent (TreeNode parent);
    List<TreeNode> findByParentId (Long parentId);
    
    @Modifying(clearAutomatically = true)
    @Transactional(readOnly=false, isolation=Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Query("update TreeNode treeNode set treeNode.number =:#{#node.number}, treeNode.parentId=:#{#node.parentId} where treeNode.id =:#{#node.id}")
    void update (@Param("node") TreeNode node);

}
