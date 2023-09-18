package Cfscript.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

// Print Node
public final class CfscriptPrintNode extends CfscriptExpressionNode {
    @Child private CfscriptExpressionNode valueNode;

    public CfscriptPrintNode(CfscriptExpressionNode valueNode) {
        this.valueNode = valueNode;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        System.out.println(valueNode.execute(frame));
        return null;
    }
}
