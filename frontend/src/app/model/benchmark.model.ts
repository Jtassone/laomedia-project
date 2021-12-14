import { Instance } from './instance.model'
import { MachineConfig } from './machineConfig.model';

export default class Benchmark {
  id: string;
  Ins?: Instance;
  machineConfig?: MachineConfig;

  // real
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

export { Benchmark }
