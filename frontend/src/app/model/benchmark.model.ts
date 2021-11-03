import { Instance } from './instance.model'
import { MachineConfig } from './machineConfig.model';

export default class Benchmark {
  id: string;
  Ins: Instance;
  machineConfig: MachineConfig;
}

export { Benchmark }
