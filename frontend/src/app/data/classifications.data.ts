import { Classification } from "../model/classification.model";
import { algoData } from "./algorithms.data";

let simpleClassifications: Classification[] = [
  {
    id: 'one',
    name: 'one',
    algos: [],
    children: [
      {
        id: 'one-a',
        name: 'one-a',
        algos: [],
        children: [],
        parentClassificationId: 'one',
      },
      {
        id: 'one-b',
        name: 'one-b',
        algos: [],
        children: [],
        parentClassificationId: 'one',
      },
      {
        id: 'one-c',
        name: 'one-c',
        algos: [],
        children: [],
        parentClassificationId: 'one',
      },
    ],
    parentClassificationId: null,
  },
  {
    id: 'two',
    name: 'two',
    algos: [],
    children: [],
    parentClassificationId: null,
  },
  {
    id: 'three',
    name: 'three',
    algos: [],
    children: [],
    parentClassificationId: null,
  },
  {
    id: 'four',
    name: 'four',
    algos: [],
    children: [],
    parentClassificationId: null,
  },
]

let algoClassification: Classification = {
  id: 'algo-classification',
  name: 'algo-classification',
  algos: [algoData, algoData, algoData],
  children: [],
  parentClassificationId: null
}

export { simpleClassifications, algoClassification }
