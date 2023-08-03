import { styled } from "styled-components";
import Button from "../components/common/button/BaseButton";
import ButtonLarge from "../components/common/button/ButtonLarge";
import ButtonSmall from "../components/common/button/ButtonSmall";

export default function Components() {
  return (
    <StyledComponents>
      <ButtonLarge type="submit" iconName="plus">
        BUTTON
      </ButtonLarge>
      <Button type="submit" outline iconName="plus">
        BUTTON
      </Button>
      <Button type="submit" ghost flexible iconName="plus">
        This is Flexible BUTTON
      </Button>
      <ButtonSmall type="submit" ghost iconName="plus">
        BUTTON
      </ButtonSmall>
    </StyledComponents>
  );
}

const StyledComponents = styled.div`
  background: ${({ theme }) => theme.color.neutral.surface.default};
`;
