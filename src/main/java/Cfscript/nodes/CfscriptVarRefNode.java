package Cfscript.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.frame.*;
// Variable Reference Node
public final class CfscriptVarRefNode extends CfscriptExpressionNode {
    private final String variableName;

    public CfscriptVarRefNode(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        var slot = frame.getFrameDescriptor().findOrAddAuxiliarySlot(variableName);
        return slot;
    }
}
