import React from "react";
import styled from "styled-components";
import { Icon, IconType, ThemeColorKeys } from "components/Common/Icon/Icon";

type IconName = keyof IconType;

interface StyledTagProps {
  $backgroundColor: string;
  $border?: boolean;
  color: ThemeColorKeys | string;
  size?: "S" | "M";
}

interface TagProps extends StyledTagProps {
  text: string;
  icon?: IconName;
  fill?: ThemeColorKeys;
  stroke?: ThemeColorKeys;
}

export const Tag: React.FC<TagProps> = ({
  text,
  color,
  $backgroundColor,
  icon,
  fill,
  stroke,
  size,
  $border,
}) => (
  <TagLayout
    color={color}
    $backgroundColor={$backgroundColor}
    $border={$border}
    size={size}
  >
    {icon && <Icon icon={icon} fill={fill} stroke={stroke} />}
    <p>{text}</p>
  </TagLayout>
);

const TagLayout = styled.div<StyledTagProps>`
  color: ${(props) => props.color};
  background-color: ${(props) => props.$backgroundColor};
  border: ${({ theme, $border }) => ($border ? theme.border.default : "none")};
  padding: ${(props) => (props.size === "S" ? "4px 8px" : "8px 16px")};
  border-radius: ${({ theme: { radius } }) => radius.large};
  border-color: ${({ theme: { color } }) => color.nuetralBorderDefault};
  font: ${({ theme: { font } }) => font.displayM12};
  display: inline-flex;
  gap: 4px;
`;
