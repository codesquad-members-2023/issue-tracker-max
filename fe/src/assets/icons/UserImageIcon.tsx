export default function UserImageIcon({ size }: { size: 'large' | 'small' }) {
  const circleSize = size === 'large' ? '16' : '10';
  return (
    <svg
      width={size === 'large' ? '32' : '20'}
      height={size === 'large' ? '32' : '20'}
      viewBox={size === 'large' ? '0 0 32 32' : '0 0 20 20'}
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <circle cx={circleSize} cy={circleSize} r={circleSize} fill="#EFF0F6" />
    </svg>
  );
}
