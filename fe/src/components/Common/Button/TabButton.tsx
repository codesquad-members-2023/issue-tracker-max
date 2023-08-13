import React, { ReactNode } from "react";
import styled from "styled-components";
import { Button } from "components/Common/Button/Button";
import { Icon, IconType } from "components/Common/Icon/Icon";

type IconName = keyof IconType;

interface TabProps {
  children: ReactNode[];
  activeTab?: string | null;
}

interface TabButtonProps {
  icon: IconName;
  text: string;
  count: number;
  $isActive?: boolean;
  onClick?: () => void;
}

export const Tab: React.FC<TabProps> = ({ children, activeTab = null }) => {
  return (
    <StyledTabLayout>
      {React.Children.map(children, (child) => {
        if (React.isValidElement<TabButtonProps>(child)) {
          return React.cloneElement(child, {
            $isActive: activeTab ? activeTab === child.props.text : false,
          });
        }
        return child;
      })}
    </StyledTabLayout>
  );
};

export const TabButton: React.FC<TabButtonProps> = ({
  icon,
  text,
  count,
  $isActive,
  onClick,
}) => (
  <Button size="S" variant="ghost" onClick={onClick}>
    <Icon
      icon={icon}
      size="M"
      stroke={$isActive ? "nuetralTextStrong" : "nuetralTextDefault"}
      fill={$isActive ? "nuetralTextStrong" : "nuetralTextDefault"}
    />
    <StyledTitle $isActive={$isActive}>{`${text} (${count})`}</StyledTitle>
  </Button>
);
const StyledTabLayout = styled.div`
  display: flex;
  align-items: center;
  height: 40px;
  flex-shrink: 0;
  border: 1px solid;
  border-radius: ${({ theme: { radius } }) => radius.medium};
  width: 320px;
  & > *:first-child {
    border-right: 1px solid;
    border-color: ${({ theme: { color } }) => color.nuetralBorderDefault};
  }
  > button {
    width: 50%;
  }
  border-color: ${({ theme: { color } }) => color.nuetralBorderDefault};
`;

const StyledTitle = styled.span<{ $isActive?: boolean }>`
  font: ${({ $isActive, theme: { font } }) =>
    $isActive ? font.selectedB16 : font.availableM16};
  color: ${({ $isActive, theme: { color } }) =>
    $isActive ? color.nuetralTextStrong : color.nuetralTextDefault};
`;
