import styled from "styled-components";

interface DropdownHeaderProps {
  title: string;
}

export const DropdownTitle: React.FC<DropdownHeaderProps> = ({ title }) => {
  return <OptionTitle>{title}</OptionTitle>;
};

const OptionTitle = styled.p`
  width: 100%;
  padding: 8px 16px;
  background-color: ${({ theme: { color } }) => color.nuetralSurfaceDefault};
  color: ${({ theme: { color } }) => color.nuetralTextWeak};
  font: ${({ theme: { font } }) => font.displayM12};
  border-radius: ${({ theme: { radius } }) => radius.large};
`;
