class Main {
    public static void sayHello (int numThreads) {
        Thread threads[] = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            final int threadNumber = i;
            threads[i] = new Thread(() -> {
                for (int j = 1; j <= 100; j++) {
                    System.out.println(j + "from thread" + threadNumber);
                    if (j % 10 == 0) {
                        //prints new line every 10 threads
                        System.out.println();
                    }
                }
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        sayHello(10);
        //
    }
    //Part II -------------------------------
    private static int answer = 0;
        public static void badSum (int numThreads) {
            answer = 0;
            int maxValue = 4000;
        Thread threads[] = new Thread[numThreads];
        
        for (int i = 0; i < numThreads; i++) {
            final int threadNumber = i;
            threads[i] = new Thread(() -> {
                int start = threadNumber + maxValue / numThreads;
                int end = Math.min((threadNumber + 1) * maxValue / numThreads, maxValue);
                for (int j = start; j < end; j++) {
                    answer += j;
                }
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int correctAnswer = (maxValue * (maxValue -1) / 2);
        System.out.println(answer);
        System.out.println(correctAnswer);
    }

}