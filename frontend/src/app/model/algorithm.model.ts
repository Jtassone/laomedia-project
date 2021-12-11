import { Instance } from "./instance.model";
import { Implementation } from "./implementation.model";

class Algorithm {
  id: string;
  ins?: Instance[];
  name: string;
  imps?: Implementation[];
  classificationId?: string;
  algorithmDetails?: string;
}

class Algorithm2 {
  id: string;
  ins?: Instance[];
  name: string;
  imps?: Implementation[];
  classificationId?: string;
  algorithmDetails?: string;
}

export { Algorithm, Algorithm2 }
