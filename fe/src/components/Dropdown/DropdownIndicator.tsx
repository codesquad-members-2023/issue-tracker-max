import styled from 'styled-components';
import { Icon } from 'components/Icon/Icon';

interface IndicatorProps {
  id: string;
  title: string;
  onLabelClick: (id: string, e: React.MouseEvent<HTMLButtonElement>) => void;
}

export const DropdownIndicator: React.FC<IndicatorProps> = ({
  id,
  title,
  onLabelClick,
}) => {
  const handleClick = (e: React.MouseEvent<HTMLButtonElement>) => {
    onLabelClick(id, e);
  };

  return (
    <Indicator onClick={handleClick}>
      <p>{title}</p>
      <Icon icon="ChevronDown" />
    </Indicator>
  );
};

const Indicator = styled.button`
  display: flex;
  width: 80px;
  height: 32px;
  justify-content: space-between;
  align-items: center;
  gap: 4px;
  flex-shrink: 0;
  transition: opacity 0.3s ease;
  color: ${({ theme: { color } }) => color.nuetralTextDefault};

  &:hover {
    opacity: ${({ theme: { opacity } }) => opacity.hover};
  }
  &:active {
    opacity: ${({ theme: { opacity } }) => opacity.press};
  }
  &:disabled {
    pointer-events: none;
    opacity: ${({ theme: { opacity } }) => opacity.disabled};
  }
`;
