package com.hades.jeval.function.math;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sourceforge.jeval.Evaluator;
import net.sourceforge.jeval.function.Function;
import net.sourceforge.jeval.function.FunctionGroup;

public class MathFunctions implements FunctionGroup {
    /**
     * Used to store instances of all of the functions loaded by this class.
     */
    private List<Function> functions = new ArrayList<Function>();

    /**
     * Default contructor for this class. The functions loaded by this class are
     * instantiated in this constructor.
     */
    public MathFunctions() {
        functions.add(new Abs());
        functions.add(new Acos());
        functions.add(new Asin());
        functions.add(new Atan());
        // functions.add(new Atan2());
        // functions.add(new Ceil());
        functions.add(new Cos());
        functions.add(new Exp());
        functions.add(new Floor());
        // functions.add(new IEEEremainder());
        functions.add(new Log());
        functions.add(new Max());
        functions.add(new Min());
        functions.add(new Pow());
        functions.add(new net.sourceforge.jeval.function.math.Random());
        functions.add(new Rint());
        // functions.add(new Round());
        functions.add(new Sin());
        functions.add(new Sqrt());
        functions.add(new Tan());
        // functions.add(new ToDegrees());
        // functions.add(new ToRadians());

        functions.add(new Mod());
    }

    /**
     * Returns the name of the function group - "numberFunctions".
     * 
     * @return The name of this function group class.
     */
    public String getName() {
        return "numberFunctions";
    }

    /**
     * Returns a list of the functions that are loaded by this class.
     * 
     * @return A list of the functions loaded by this class.
     */
    public List<Function> getFunctions() {
        return functions;
    }

    /**
     * Loads the functions in this function group into an instance of Evaluator.
     * 
     * @param evaluator
     *            An instance of Evaluator to load the functions into.
     */
    public void load(final Evaluator evaluator) {
        Iterator<Function> functionIterator = functions.iterator();

        while (functionIterator.hasNext()) {
            evaluator.putFunction((Function) functionIterator.next());
        }
    }

    /**
     * Unloads the functions in this function group from an instance of
     * Evaluator.
     * 
     * @param evaluator
     *            An instance of Evaluator to unload the functions from.
     */
    public void unload(final Evaluator evaluator) {
        Iterator<Function> functionIterator = functions.iterator();

        while (functionIterator.hasNext()) {
            evaluator.removeFunction(((Function) functionIterator.next()).getName());
        }
    }
}
