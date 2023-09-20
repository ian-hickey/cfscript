package Cfscript.nodes;
import Cfscript.config.CfscriptLanguage;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.frame.VirtualFrame;

public class CfscriptExpressionNode extends CfscriptNode {
    protected CfscriptExpressionNode(CfscriptLanguage language, FrameDescriptor frameDescriptor) {
    }

    protected CfscriptExpressionNode() {
    }

    public Object execute(VirtualFrame frame) {
        return null;
    }
}
