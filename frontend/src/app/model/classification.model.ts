import Algorithm from "./algorithm.model";

class Classification {
  id: string;
  name: string;
  algos: Algorithm[];
  children: Classification[]

  constructor(id: string, name: string, algos: Algorithm[], children: Classification[]) {
    this.id = id;
    this.name = name;
    this.algos = algos;
    this.children = children;
  }
}

export { Classification }
