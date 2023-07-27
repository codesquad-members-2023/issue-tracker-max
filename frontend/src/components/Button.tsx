import { styled } from 'styled-components';
import Icons from '../design/Icons';

type ButtonProps = {
  iconName?: keyof typeof Icons;
  text: string;
} & StyledProps;

type StyledProps = {
  // theme: Record<string, unknown>;
  $Flexible: 'Flexible' | 'Fixed';
  $Type: 'Contained' | 'Outline' | 'Ghost';
  $ElementPattern: 'TextOnly' | 'Icon+Text';
  $State: 'Enabled' | 'Hover' | 'Press' | 'Disabled' | 'Selected/Active';
  $Size: 'L' | 'M' | 'S';
};

export default function Button({
  $Flexible,
  $Type,
  $ElementPattern,
  $State,
  $Size,
  iconName,
  text,
}: ButtonProps) {
  const Icon = iconName && toStyledIcon(iconName);

  return (
    <Container
      $Flexible={$Flexible}
      $Type={$Type}
      $ElementPattern={$ElementPattern}
      $State={$State}
      $Size={$Size}>
      {Icon && $ElementPattern === 'Icon+Text' && (
        <Icon {...{ $Type, $Size, $State }} />
      )}
      <TextLabel {...{ $Type, $State, $Size }}>{text}</TextLabel>
    </Container>
  );
}

const Container = styled.button<StyledProps>`
  cursor: pointer;
  box-sizing: border-box;
  display: inline-flex;
  align-items: center;
  justify-content: center;

  ${({ $Flexible }) =>
    $Flexible === 'Flexible' ? '' : 'width: auto !important;'};

  ${({ theme, $Type }) => {
    switch ($Type) {
      case 'Contained':
        return `
          color: ${theme.color.brand.text.default};
          background-color: ${theme.color.brand.surface.default};
          border: none;
        `;
      case 'Outline':
        return `
          color: ${theme.color.brand.text.weak};
          border: 1px solid ${theme.color.brand.surface.default};
          background-color: transparent;
        `;
      case 'Ghost':
        return `
          color: ${theme.color.neutral.text.default};
          border: none;
          background-color: transparent;
        `;
    }
  }}

  opacity: ${({ theme, $State }) => {
    switch ($State) {
      case 'Selected/Active':
      case 'Enabled':
        return 1;
      case 'Hover':
        return theme.objectStyles.opacity.hover;
      case 'Press':
        return theme.objectStyles.opacity.press;
      case 'Disabled':
        return theme.objectStyles.opacity.disabled;
    }
  }};

  ${({ theme, $Type, $Size }) => {
    if ($Type === 'Ghost') {
      return ``;
    }
    switch ($Size) {
      case 'L':
        return `
          min-width: 240px;
          height: 56px;
          padding: 0 24px;
          border-radius: ${theme.objectStyles.radius.large};
        `;
      case 'M':
        return `
          min-width: 184px;
          height: 48px;
          padding: 0 24px;
          border-radius: ${theme.objectStyles.radius.medium};
        `;
      case 'S':
        return `
          min-width: 128px
          height: 40px;
          padding: 0 16px;
          border-radius: ${theme.objectStyles.radius.medium};
        `;
    }
  }}
`;

const TextLabel = styled.span<{
  $Size: 'L' | 'M' | 'S';
  $Type: 'Contained' | 'Outline' | 'Ghost';
  $State: 'Enabled' | 'Hover' | 'Press' | 'Disabled' | 'Selected/Active';
}>`
  ${({ theme, $Size }) => {
    switch ($Size) {
      case 'L':
        return `
          ${theme.font.available.medium[20]}
          padding: 0 8px;
        `;
      case 'M':
        return `
          ${theme.font.available.medium[16]}
          padding: 0 8px;
        `;
      case 'S':
        return `
          ${theme.font.available.medium[12]}
          padding: 0 4px;
        `;
    }
  }}

  ${({ theme, $Type, $State, $Size }) => {
    const fontStyle = `
      color: ${theme.color.neutral.text.strong};
    `;

    if ($Type !== 'Ghost' || $State !== 'Selected/Active') {
      return '';
    }
    switch ($Size) {
      case 'L':
        return (
          fontStyle +
          `
          ${theme.font.selected.bold[20]}
          
        `
        );
      case 'M':
        return (
          fontStyle +
          `
          ${theme.font.selected.bold[16]}
        `
        );
      case 'S':
        return (
          fontStyle +
          `
          ${theme.font.selected.bold[12]}
        `
        );
    }
  }}
`;

function toStyledIcon(iconName: keyof typeof Icons) {
  return styled(Icons[iconName])<{
    $Type: 'Contained' | 'Outline' | 'Ghost';
    $Size: 'L' | 'M' | 'S';
    $State: 'Enabled' | 'Hover' | 'Press' | 'Disabled' | 'Selected/Active';
  }>`
    width: ${({ $Size }) => ($Size === 'L' ? '24px' : '16px')};
    height: ${({ $Size }) => ($Size === 'L' ? '24px' : '16px')};
    stroke: ${({ theme, $Type }) => {
      switch ($Type) {
        case 'Contained':
          return theme.color.brand.text.default;
        case 'Outline':
          return theme.color.brand.text.weak;
        case 'Ghost':
          return theme.color.neutral.text.default;
      }
    }};
    fill: ${({ theme, $Type }) => {
      switch ($Type) {
        case 'Contained':
          return theme.color.brand.text.default;
        case 'Outline':
          return theme.color.brand.text.weak;
        case 'Ghost':
          return theme.color.neutral.text.default;
      }
    }};

    ${({ theme, $Type, $State }) => {
      if ($Type === 'Ghost' && $State === 'Selected/Active') {
        return `
          stroke: ${theme.color.neutral.text.strong};
          fill: ${theme.color.neutral.text.strong};
        `;
      }
    }}
  `;
}
