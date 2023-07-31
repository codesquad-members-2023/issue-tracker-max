export default function CheckBoxIcon({
  status,
}: {
  status: 'initial' | 'disable' | 'active';
}) {
  return (
    <svg
      width="16px"
      height="16px"
      viewBox="0 0 16 16"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
    >
      <rect
        x={status === 'initial' ? '0.8' : '0.5'}
        y={status === 'initial' ? '0.8' : '0.5'}
        width={status === 'initial' ? '14.4px' : '15px'}
        height={status === 'initial' ? '14.4px' : '15px'}
        rx={status === 'initial' ? '1.2px' : '1.5px'}
        stroke={status === 'initial' ? '#D9DBE9' : '#007AFF'}
        strokeWidth={status === 'initial' ? '1.6px' : 'none'}
      />
      {status !== 'initial' && (
        <path
          d={
            status === 'disable' ? 'M6 8H10' : 'M10.6667 6L7 9.67333L5 7.67333'
          }
          stroke="#FEFEFE"
          strokeWidth="1.6px"
          strokeLinecap="round"
          strokeLinejoin="round"
        />
      )}
    </svg>
  );
}
