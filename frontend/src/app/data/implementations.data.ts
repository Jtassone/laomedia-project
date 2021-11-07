import { Implementation } from '../model/implementation.model';
import { instList } from './instances.data';
import { benchList } from './benchmarks.data';

let impData: Implementation = {
  id: 'sample-imp',
  name: 'sample-imp',
  instances: instList,
  BenchmarkResults: benchList
}

export { impData };
