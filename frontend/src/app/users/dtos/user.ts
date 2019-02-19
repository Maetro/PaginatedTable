import { Role } from './role';

export interface User {
  id: number;
  name: string;
  surname: string;
  userStatus: UserStatusEnum;
  birthdate: Date;
  numberOfChildren: number;
  score: number;
  roles: Role[];
}

export enum UserStatusEnum {
  ACTIVE = 1,
  PENDING = 2,
  DELETED = 3
}
