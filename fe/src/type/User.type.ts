export type User = {
  accessToken: string;
  refreshToken: string;
  member: {
    id: number;
    name: string;
    imageUrl: string;
  };
};
