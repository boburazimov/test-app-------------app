export interface IAttachment {
  id?: number;
  path?: string | null;
  originalFileName?: string | null;
  fileName?: string | null;
  contentType?: string | null;
  fileSize?: number | null;
}

export const defaultValue: Readonly<IAttachment> = {};
