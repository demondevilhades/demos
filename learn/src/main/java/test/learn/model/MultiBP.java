package test.learn.model;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import test.learn.exception.InitException;
import test.learn.util.Function;

/**
 * multiple bpnn
 * 
 * @author hades
 */
public class MultiBP {

    private Brain brain = null;
    private Map<double[], double[]> dataMap = null;

    public MultiBP() {
    }

    public void initBrain(int[] size, double[] e) {
        this.brain = new Brain(size, e);
    }

    public void initData(Map<double[], double[]> dataMap) {
        this.dataMap = dataMap;
    }

    public double train(int times) {
        if (brain == null) {
            throw new InitException("[ERROR]brain not init!");
        }
        if (dataMap == null) {
            throw new InitException("[ERROR]dataMap not init!");
        }
        Set<Entry<double[], double[]>> entrySet = dataMap.entrySet();
        for (Map.Entry<double[], double[]> entry : entrySet) {
            if (entry.getKey().length != brain.size[0] || entry.getValue().length != brain.size[brain.size.length - 1]) {
                throw new InitException("[ERROR]dataMap not match the current brain!");
            }
        }

        entrySet = dataMap.entrySet();
        double result = 0.0d;
        while (times-- > 0) {
            for (Map.Entry<double[], double[]> entry : entrySet) {
                result = brain.train(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    public double[] getResult(double[] input) {
        return brain.expect(input);
    }

    private class Brain {

        private int[] size;

        /**
         * weight matrix
         */
        private double[][][] w;

        /**
         * threshold
         */
        private double[][] b;

        /**
         * learning rate
         */
        private double[] e;

        private Random random;

        private Brain(int[] size, double[] e) {
            init(size, e);
        }

        private void init(int[] size, double[] e) {
            this.size = size;
            this.e = e;
            random = new Random();

            int layerNum = size.length - 1;

            w = new double[layerNum][][];
            for (int i = 0; i < layerNum; i++) {
                w[i] = new double[size[i]][size[i + 1]];
                initW(w[i]);
            }
            b = new double[layerNum][];
            for (int i = 0; i < layerNum; i++) {
                b[i] = new double[size[i + 1]];
                initB(b[i]);
            }
        }

        private void initW(double[][] w) {
            int jSize = w[0].length;
            int iSize = w.length;
            double nextDouble;
            for (int i = 0; i < iSize; i++) {
                for (int j = 0; j < jSize; j++) {
                    nextDouble = random.nextDouble();
                    while (nextDouble == 0.0d) {
                        nextDouble = random.nextDouble();
                    }
                    w[i][j] = nextDouble * 2 - 1;
                }
            }
        }

        private void initB(double[] b) {
            int size = b.length;
            double nextDouble;
            for (int i = 0; i < size; i++) {
                nextDouble = random.nextDouble();
                while (nextDouble == 0.0d) {
                    nextDouble = random.nextDouble();
                }
                b[i] = nextDouble * 2 - 1;
            }
        }

        public double train(double[] inputData, double[] resultData) {
            double err = 0.0d;

            double[][] layer = new double[size.length][];
            layer[0] = inputData;

            for (int i = 1; i < size.length; i++) {
                layer[i] = new double[size[i]];
                calcResult(layer[i - 1], layer[i], w[i - 1], b[i - 1]);
            }

            int lastLayerIndex = layer.length - 1;
            double[][] revise = new double[lastLayerIndex][];
            double[] outputLayer = layer[lastLayerIndex];
            double[] outputHideLayer = layer[lastLayerIndex - 1];
            revise[lastLayerIndex - 1] = new double[outputLayer.length];
            for (int i = 0; i < outputLayer.length; i++) {
                revise[lastLayerIndex - 1][i] = outputLayer[i] * (1 - outputLayer[i])
                                * (resultData[i] - outputLayer[i]);
                err += Math.pow((resultData[i] - outputLayer[i]), 2);
                for (int j = 0; j < outputHideLayer.length; j++) {
                    w[lastLayerIndex - 1][j][i] += e[lastLayerIndex - 1] * revise[lastLayerIndex - 1][i]
                                    * outputHideLayer[j];
                }
                b[lastLayerIndex - 1][i] = e[lastLayerIndex - 1] * revise[lastLayerIndex - 1][i];
            }

            for (int i = lastLayerIndex - 2; i >= 0; i--) {
                revise[i] = revise_ih(revise[i + 1], layer[i], layer[i + 1], layer[i + 1].length, layer[i + 2].length,
                                layer[i].length, w[i + 1], w[i], b[i], e[i]);
            }

            return err;
        }

        /**
         * calculate layer
         * 
         * @param input
         * @param output
         * @param w
         * @param b
         */
        private void calcResult(double[] input, double[] output, double[][] w, double[] b) {
            double sum;
            for (int i = 0; i < output.length; i++) {
                sum = 0;
                for (int j = 0; j < input.length; j++) {
                    sum += input[j] * w[j][i];
                }
                sum += b[i];
                output[i] = Function.sigmoid(sum);
            }
        }

        /**
         * revise
         * 
         * @param reviseOutput
         * @param input
         * @param hide
         * @param hideSize
         * @param outputSize
         * @param inputSize
         * @param w_ho
         * @param w
         * @param b
         * @param e
         * @return
         */
        private double[] revise_ih(double[] reviseOutput, double[] input, double[] hide, int hideSize, int outputSize,
                        int inputSize, double[][] w_ho, double[][] w, double[] b, double e) {
            double sum;
            double[] reviceHide = new double[hideSize];
            for (int i = 0; i < hideSize; i++) {
                sum = 0;
                for (int j = 0; j < outputSize; j++) {
                    sum += reviseOutput[j] * w_ho[i][j];
                }
                reviceHide[i] = hide[i] * (1 - hide[i]) * sum;
                for (int j = 0; j < inputSize; j++) {
                    w[j][i] += e * reviceHide[i] * input[j];
                }
                b[i] = e * reviceHide[i];
            }
            return reviceHide;
        }

        public double[] expect(double[] input) {
            double[][] layer = new double[size.length][];
            layer[0] = input;
            for (int i = 0; i < layer.length - 1; i++) {
                layer[i + 1] = new double[size[i + 1]];
                expect(layer[i], layer[i + 1], w[i], b[i]);
            }
            return layer[layer.length - 1];
        }

        private void expect(double[] input, double[] output, double[][] w, double[] b) {
            for (int i = 0; i < output.length; i++) {
                output[i] = 0.0d;
                for (int j = 0; j < input.length; j++) {
                    output[i] += input[j] * w[j][i];
                }
                output[i] += b[i];
                output[i] = Function.sigmoid(output[i]);
            }
        }
    }
}
