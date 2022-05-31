import { IProduct } from 'app/shared/model/product.model';

export interface IBarcode {
  id?: number;
  code?: string;
  product?: IProduct | null;
}

export const defaultValue: Readonly<IBarcode> = {};
