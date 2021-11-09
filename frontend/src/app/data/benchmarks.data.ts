import { Benchmark } from '../model/benchmark.model';
import { MachineConfig } from '../model/machineConfig.model';

let defConfig: MachineConfig = {
  id: 'sample-machine-config',
  core: 'like four?',
  CPU: 'A good one',
  L1: 'Cache',
  L2: 'More Cache',
  L3: 'Even More Cache',
  RAM: '8GB SSD'
}

let benchData: Benchmark = {
  id: 'sample-benchmark',
  Ins: null,
  machineConfig: defConfig
}

let benchList: Benchmark[] = [
  benchData,
  benchData,
  benchData,
  benchData,
  benchData,
  benchData,
]

export { MachineConfig, benchData, benchList }
