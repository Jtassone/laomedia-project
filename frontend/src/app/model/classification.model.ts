import Algorithm from "./algorithm.model";

class Classification {
  id: string;
  name: string;
  algos: Algorithm;
  children: Classification[]
}

export { Classification }
