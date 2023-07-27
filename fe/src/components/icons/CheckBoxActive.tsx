type Props = {
  width?: number;
  height?: number;
  stroke?: string;
  fill?: string;
};

export const CheckBoxActive: React.FC<Props> = ({}: Props) => {
  return (
    <svg
      width="16"
      height="16"
      viewBox="0 0 16 16"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <rect
        x="0.5"
        y="0.5"
        width="15"
        height="15"
        rx="1.5"
        fill="#007AFF"
        stroke="#007AFF"
      />
      <path
        d="M10.6667 6L7 9.67333L5 7.67333"
        stroke="#FEFEFE"
        stroke-width="1.6"
        stroke-linecap="round"
        stroke-linejoin="round"
      />
    </svg>
  );
};
