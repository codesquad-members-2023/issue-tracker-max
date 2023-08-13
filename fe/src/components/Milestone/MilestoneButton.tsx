import { css } from '@emotion/react';

interface Props extends React.HTMLAttributes<HTMLButtonElement> {
  color: string;
  icon: React.ReactNode;
  children: React.ReactNode;
}

export default function MilestoneButton({
  color,
  icon,
  children,
  onClick,
}: Props) {
  return (
    <button type="button" css={button(color)} onClick={onClick}>
      {icon}
      {children}
    </button>
  );
}

const button = (color: string) => css`
  color: ${color};

  & svg path {
    stroke: ${color};
  }
`;
