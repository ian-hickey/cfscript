package Cfscript.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;

// Assignment Node
@NodeInfo(shortName = "=")
public final class CfscriptAssignmentNode extends CfscriptExpressionNode {
    private final String variableName;
    @Child private CfscriptExpressionNode valueNode;

    public CfscriptAssignmentNode(String variableName, CfscriptExpressionNode valueNode) {
        this.variableName = variableName;
        this.valueNode = valueNode;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Object value = valueNode.execute(frame);
        // Set the variable in the frame

        var slot = frame.getFrameDescriptor().findOrAddAuxiliarySlot(variableName);
        frame.setObject(slot, value);
        return value;
    }
}
