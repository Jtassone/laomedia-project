import { Instance } from "./instance.model";
import { Implementation } from "./implementation.model";

export default class Algorithm {
  id: string;
  ins?: Instance[];
  name: string;
  imps?: Implementation[];
  classificationId?: string;
  algorithmDetails?: string;
}

export { Algorithm }
