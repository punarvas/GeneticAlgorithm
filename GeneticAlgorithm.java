import java.util.Scanner;
import java.util.Random;


public class GeneticAlgorithm {
    private int populationSize;
    private double crossoverRate = -1;
    private double mutationRate = -1;
    private int genomeLength;
    private int[][] population;
    private final Random random = new Random();

    private int[] replacement = new int[2];

    public GeneticAlgorithm(int populationSize, int genomeLength, double crossoverRate, double mutationRate) {
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.genomeLength = genomeLength;
        this.population = new int[this.populationSize][this.genomeLength];

        // Initialize population
        this.makePopulation();
    }

    public GeneticAlgorithm(int populationSize, int genomeLength, double mutationRate) {
        // NOTE: Use this constructor when crossover rate must be turned off
        this.populationSize = populationSize;
        this.genomeLength = genomeLength;
        this.crossoverRate = 0.0;
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
        int totalFitness = 0;
        double bestFitness = 0;
        for (int i = 0; i < this.populationSize; i++){
            int fitnessValue = fitness(this.population[i]);
            totalFitness += fitnessValue;
            if (fitnessValue >= bestFitness) {
                bestFitness = fitnessValue;
            }
        }
        evaluation[0] = totalFitness / this.populationSize;
        evaluation[1] = bestFitness;
        return evaluation;
    }

    private int[][] rouletteWheel(int n) {
        int totalFitness = 0;
        int[] fitnessValues = new int[this.populationSize];
        int[][] pairs = new int[n][this.genomeLength];

        for (int i = 0; i < this.populationSize; i++) {
            fitnessValues[i] = fitness(this.population[i]); 
            totalFitness += fitnessValues[i];
        }
        double threshold = this.random.nextDouble() * totalFitness;
        int cumFitness = 0;
        int k = 0;

        for (int i = 0; i < n; i++) {
            cumFitness += fitnessValues[i];
            if (cumFitness >= threshold && k < n) {
                pairs[k] = this.population[i];
                replacement[k] = i;
                k += 1;
            }
        }
        return pairs;
    }

    private int[][] selectPair() {
        int[][] pair = new int[2][this.genomeLength];
        for (int i = 0; i < 2; i++) {
            int randomIndex = this.random.nextInt(this.populationSize);
            pair[i] = this.population[randomIndex];
            replacement[i] = randomIndex;
        }
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
        int[] mutatedGenome = genome;
        for (int i = 0; i < this.genomeLength; i++) {
            double rate = this.random.nextDouble();
            if (rate < this.mutationRate) {
                mutatedGenome[i] = this.random.nextInt(2);
            }
        }
        return mutatedGenome;
    }
    

    public void runGA() {
        int maxGenerations = 30;
        int targetFitness = 10;

        for(int i = 0; i < maxGenerations; i++) {
            double[] evaluation = this.evaluateFitness();
            if (evaluation[1] == targetFitness) {
                this.printGeneration(i + 1);
                break;
            }

            // Pick a pair of individual
            // int[][] genomePair = this.selectPair();
            int[][] genomePair = this.rouletteWheel(2);

            // Produce child population
            int[][] childGenome = this.crossover(genomePair[0], genomePair[1]);

            // Mutate the child population
            for (int j = 0; j < 2; j++) {
                childGenome[j] = this.mutate(childGenome[j]);
            }

            // replace the population
            this.population[replacement[0]] = childGenome[0];
            this.population[replacement[1]] = childGenome[1];

            // Print generation report
            this.printGeneration(i + 1);
        }
    }

    public void printGeneration(int gen) {
        double[] evaluation = this.evaluateFitness();
        System.out.print("Generation " + gen + ": ");
        System.out.print("average fitness: " + evaluation[0] + ", ");
        System.out.print("best fitness: " + evaluation[1] + "\n");
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

        // System.out.println(populationSize + ", " + mutationRate + ", " + crossoverRate +
        //  ", " + genomeLength);
        GeneticAlgorithm ga = new GeneticAlgorithm(populationSize, genomeLength, mutationRate, crossoverRate);
        ga.runGA();
    }
}