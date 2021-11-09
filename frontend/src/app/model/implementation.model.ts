import { Benchmark } from './benchmark.model';
import { Instance } from './instance.model';

export default class Implementation {
  id: string;
  name: string;
  instances: Instance[];
  BenchmarkResults: Benchmark[];
}

export { Implementation }
