import { Benchmark } from './benchmark.model';
import { Instance } from './instance.model';

export default class Implementation {
  id: string;
  name: string;
  instances?: Instance[];
  BenchmarkResults?: Benchmark[];
  algorithmId?: string;
  implementationDetails?: string;
}

export { Implementation }
