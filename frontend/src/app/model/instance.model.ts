import Benchmark from "./benchmark.model";

class Instance {
  id: string;
  name: string;
  instanceFileString?: string;
  algorithmId?: string;
  implementationId?: string;
  benchmarks?: Benchmark[];
}

export { Instance }
