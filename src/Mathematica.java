import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.MathLinkFactory;

/**
 * Created with IntelliJ IDEA.
 * User: kenli
 * Date: 3/3/13
 * Time: 2:08 午後
 * To change this template use File | Settings | File Templates.
 */
public class Mathematica {

    public Mathematica() {
        try {
            kl = MathLinkFactory.createKernelLink("-linkmode launch -linkname '\"/Applications/Mathematica.app/Contents/MacOS/MathKernel\" -mathlink'");
            kl.discardAnswer();
        } catch (MathLinkException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public String call(String input) {
        String result = kl.evaluateToOutputForm(input, 0);

        return result;
    }

    private KernelLink kl;
}
