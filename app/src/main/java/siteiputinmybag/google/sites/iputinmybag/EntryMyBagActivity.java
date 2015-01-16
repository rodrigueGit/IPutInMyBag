package siteiputinmybag.google.sites.iputinmybag;

import siteiputinmybag.google.sites.iputinmybag.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Stack;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class EntryMyBagActivity extends Activity {

    private Stack<String> bag;
    private Stack<String> input;

    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_entry_my_bag);
        bag = new Stack<String>();
        input = new Stack<String>();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
        TextView textView = (TextView) findViewById(R.id.bagsize);
        textView.setText("0");
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };


    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {

    }

    public void gameOver() {
        ImageButton gameOver = (ImageButton) findViewById(R.id.imagebutton);
        gameOver.setVisibility(ImageButton.VISIBLE);

        Button input = (Button) findViewById(R.id.inputbutton);
        input.setActivated(false);
        Button end = (Button) findViewById(R.id.inputbutton);
        end.setActivated(false);
    }

    /** Called when the user clicks the Send button */
    public void clickButton(View view) {
        // Do something in response to button
    }

    /** Called when the user clicks the Reset button */
    public void resetButton(View view) {
        bag.removeAllElements();
        input.removeAllElements();
        TextView textView = (TextView) findViewById(R.id.bagsize);
        textView.setText(String.valueOf(bag.size()));
        Button input = (Button) findViewById(R.id.inputbutton);
        input.setActivated(true);
        Button end = (Button) findViewById(R.id.endbutton);
        end.setActivated(true);
        TextView textInput = (TextView) findViewById(R.id.baginput);
        textInput.setText("");
        ImageButton gameOver = (ImageButton) findViewById(R.id.imagebutton);
        gameOver.setVisibility(ImageButton.INVISIBLE);
    }

    /** Called when the user clicks the Input button */
    public void inputButton(View view) {
        TextView textInput = (TextView) findViewById(R.id.baginput);
        input.push(textInput.getText().toString());
        textInput.setText("");
    }

    /** Called when the user clicks the Input button */
    public void endButton(View view) {
        int bagSize;
        int inputSize;
        int index;
        boolean different;

        bagSize = bag.size();
        inputSize = input.size();
        index = 0;
        different = false;

        if (inputSize == bagSize + 1) {
            for (String str : bag) {
                if (input.elementAt(index).compareTo(str) != 0) {
                    different = true;
                }
                index = index + 1;
            }
            bag.push(input.lastElement());
            TextView textView = (TextView) findViewById(R.id.bagsize);
            textView.setText(String.valueOf(bag.size()));
            input.removeAllElements();
        } else {
            gameOver();
        }
    }
}
