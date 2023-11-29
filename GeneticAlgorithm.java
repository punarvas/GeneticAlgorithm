import java.util.Scanner;
import java.util.Random;

public class GeneticAlgorithm {
    private int populationSize;
    private double crossoverRate = -1;
    private double mutationRate = -1;
    private int genomeLength;
    private int[][] population;
    private final Random random = new Random();

    public GeneticAlgorithm(int populationSize, int genomeLength, double crossoverRate, double mutationRate) {
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.genomeLength = genomeLength;
        this.population = new int[this.populationSize][this.genomeLength];

        // Initialize population
        this.makePopulation();
    }

    public GeneticAlgorithm(int populationSize, double mutationRate) {
        // NOTE: Use this constructor when crossover rate must be turned off
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
    }

    private int[] randomGenome(int length) {
        // Random random = new Random();
        int[] genome = new int[length];

        for (int i = 0; i < length; i++) {
            genome[i] = this.random.nextInt(2);
        }

        return genome;
    }

    private void makePopulation() {
        for (int i = 0; i < this.populationSize; i++) {
            int[] genome = this.randomGenome(10);
            this.population[i] = genome;
        }
    }

    private int fitness(int[] genome) {
        int fitnessValue = 0;
        for (int i = 0; i < this.genomeLength; i++) {
            fitnessValue += genome[i];
        }
        return fitnessValue;
    }

    private double[] evaluateFitness() {
        double[] evaluation = new double[2];

        return evaluation;
    }

    private int[][] selectPair() {
        int[][] pair = new int[2][this.genomeLength];

        return pair;
    }

    private int[][] crossover(int[] d, int[] s) {
        // mutation of dominent 'd' and submissive 's'
        int[][] childGenome = new int[2][this.genomeLength];
        int randomSplitIndex = random.nextInt(10);

        int[] firstChild = new int[this.genomeLength];
        for (int i = 0; i <= randomSplitIndex; i++) {
            firstChild[i] = d[i];
        }
        for (int i = randomSplitIndex; i < this.genomeLength; i++) {
            firstChild[i] = s[i];
        }
        
        int[] secondChild = new int[this.genomeLength];
        for (int i = 0; i <= randomSplitIndex; i++) {
            secondChild[i] = s[i];
        }
        for (int i = randomSplitIndex; i < this.genomeLength; i++) {
            secondChild[i] = d[i];
        }

        childGenome[0] = firstChild;
        childGenome[1] = secondChild;
        
        return childGenome;
    }

    private int[] mutate(int[] genome) {
        return genome;
    }

    

    public void runGA() {
        // TODO: implement running of Genetic Algorithm
    }

    /*
     * Getter functions
    */
    public int getPopulationSize() {
        return this.populationSize;
    }

    public double getCrossoverRate() {
        if (this.crossoverRate == -1) {
            return 0.0;
        }
        return this.crossoverRate;
    }

    public double getMutationRate() {
        return this.mutationRate;
    }

    public int[][] getPopulation() {
        return this.population;
    }

    public int getGenomeLength() {
        return this.genomeLength;
    }

    public static void main(String[] args) {

        Scanner stdIn = new Scanner(System.in);
        int populationSize = stdIn.nextInt();
        double mutationRate = stdIn.nextDouble();
        double crossoverRate = stdIn.nextDouble();
        int genomeLength = stdIn.nextInt();
        stdIn.close();

        System.out.println(populationSize + ", " + mutationRate + ", " + crossoverRate +
         ", " + genomeLength);
    }
}