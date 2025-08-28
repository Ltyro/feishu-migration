package com.lark.service;

import com.lark.data.pojo.Node;
import java.util.Map;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class NodeService {
    public NodeService() {
    }

    public int getLevel(String rootToken, Node node, Map<String, Node> nodeMap) {
        if (Objects.equals(node.getParentToken(), rootToken)) {
            return 1;
        } else {
            Node parent = (Node)nodeMap.get(node.getParentToken());
            return 1 + this.getLevel(rootToken, parent, nodeMap);
        }
    }
}
