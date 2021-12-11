import {Algorithm2} from "./algorithm.model";

class Classification {
  id: string;
  name: string;
  algos?: Algorithm2[];
  children?: Classification[]
  subClassificationId?: string;
  parentClassificationId: string;

  constructor(id: string, name: string, algos: Algorithm2[], children: Classification[], parent: string) {
    this.id = id;
    this.name = name;
    this.algos = algos;
    this.children = children;
    this.parentClassificationId = parent;
  }
}

export { Classification }
