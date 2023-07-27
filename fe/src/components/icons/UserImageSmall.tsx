type Props = {
  width?: number;
  height?: number;
  fill?: string;
};

export const UserImageSmall: React.FC<Props> = ({}: Props) => {
  return (
    <svg
      width="20"
      height="20"
      viewBox="0 0 20 20"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <circle cx="10" cy="10" r="10" fill="#EFF0F6" />
    </svg>
  );
};
