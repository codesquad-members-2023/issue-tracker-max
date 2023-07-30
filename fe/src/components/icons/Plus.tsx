type Props = {
  width?: number;
  height?: number;
  stroke?: string;
};

export const Plus: React.FC = ({}: Props) => {
  return (
    <svg
      width="16"
      height="16"
      viewBox="0 0 16 16"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <path
        d="M8 3.33337V12.6667"
        stroke="#4E4B66"
        stroke-width="1.6"
        stroke-linecap="round"
        stroke-linejoin="round"
      />
      <path
        d="M3.3335 8H12.6668"
        stroke="#4E4B66"
        stroke-width="1.6"
        stroke-linecap="round"
        stroke-linejoin="round"
      />
    </svg>
  );
};
