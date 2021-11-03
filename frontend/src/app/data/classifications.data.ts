import { Classification } from "../model/classification.model";

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
        children: []
      },
      {
        id: 'one-b',
        name: 'one-b',
        algos: [],
        children: []
      },
      {
        id: 'one-c',
        name: 'one-c',
        algos: [],
        children: []
      },
    ]
  },
  {
    id: 'two',
    name: 'two',
    algos: [],
    children: []
  },
  {
    id: 'three',
    name: 'three',
    algos: [],
    children: []
  },
  {
    id: 'four',
    name: 'four',
    algos: [],
    children: []
  },
]

export { simpleClassifications }
