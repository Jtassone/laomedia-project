import { Algorithm2 } from '../model/algorithm.model';
import { impData } from './implementations.data';
import { instList } from './instances.data';

let algoData: Algorithm2 = {
  id: "algo-data-1",
  ins: instList,
  name: "algo-data-1",
  imps: [impData, impData, impData, impData]
}

export { algoData };
