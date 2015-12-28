package test.learn.model;

import java.util.Random;

public class SimpleRBF {

    private class Brain {

        private double[] hide;

        /**
         * weight matrix from input to hide
         */
        private double[][] w_ih;
        /**
         * weight matrix from hide to output
         */
        private double[][] w_ho;
        /**
         * threshold of hide
         */
        private double[] b_h;
        /**
         * threshold of output
         */
        private double[] b_o;

        /**
         * learning rate from input to hide
         */
        private double e_ih;
        /**
         * learning rate from hide to output
         */
        private double e_ho;

        private double rate;

        private int times;

        private Random random;

        public Brain() {

        }
    }
}
