package Cfscript.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

// Variable Reference Node
public final class CfscriptVarRefNode extends CfscriptExpressionNode {
    private final String variableName;

    public CfscriptVarRefNode(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return frame.getValue(frame.getFrameDescriptor().findOrAddAuxiliarySlot(variableName));
    }
}
