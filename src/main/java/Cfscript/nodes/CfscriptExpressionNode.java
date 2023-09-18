package Cfscript.nodes;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.frame.VirtualFrame;

public abstract class CfscriptExpressionNode extends CfscriptNode {
    public abstract Object execute(VirtualFrame frame);
}
