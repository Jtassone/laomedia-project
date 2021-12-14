import Benchmark from "./benchmark.model";

class Instance {
  id: string;
  name: string;
  instanceFileString?: string;
  algorithmId?: string;
  // implementationId?: string;
  benchmarks?: Benchmark[];
  // real ones
  date?: string;
  instanceId?: string;
  implementationId?: string;
  core?: string;
  cpu?: string;
  l1?: string;
  l2?: string;
  l3?: string;
  numberThreads?: string;
  ram?: string;
}

export { Instance }

// "date"
// "instanceId"
// "implementationId"
// "core"
// "cpu"
// "l1"
// "l2"
// "l3"
// "numberThreads"
// "ram"
