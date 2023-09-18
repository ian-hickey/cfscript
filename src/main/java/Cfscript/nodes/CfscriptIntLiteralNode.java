package Cfscript.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

// Integer Literal Node
public final class CfscriptIntLiteralNode extends CfscriptExpressionNode {
    private final int value;

    public CfscriptIntLiteralNode(int value) {
        this.value = value;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return value;
    }
}
