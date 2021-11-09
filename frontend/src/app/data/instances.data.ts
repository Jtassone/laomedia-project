import { Instance } from '../model/instance.model';
import { benchList } from './benchmarks.data';

let instData: Instance = {
  id: 'sample-instance',
  name: 'sample-instance',
  benchmarks: benchList
}

let instList: Instance[] = [
  {
    id: 'sample-instance-1',
    name: 'sample-instance-1',
    benchmarks: benchList
  },
  {
    id: 'sample-instance-2',
    name: 'sample-instance-2',
    benchmarks: benchList
  },
  {
    id: 'sample-instance-3',
    name: 'sample-instance-3',
    benchmarks: benchList
  },
  {
    id: 'sample-instance-4',
    name: 'sample-instance-4',
    benchmarks: benchList
  },
  {
    id: 'sample-instance-5',
    name: 'sample-instance-5',
    benchmarks: benchList
  },
]

export { instData, instList }
