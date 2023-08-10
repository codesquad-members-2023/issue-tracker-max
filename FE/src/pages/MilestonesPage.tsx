import { styled } from "styled-components";
import Button from "../components/common/Button/Button";
import { useNavigate } from "react-router-dom";

export default function MilestonesPage() {
  const navigate = useNavigate();
  const goLabelsPage = () => {
    navigate("/labels");
  };

  const goMilestonesPage = () => {
    navigate("/milestones");
  };

  return (
    <Main>
      <Tap>
        <TapButtonWrapper>
          <Button
            icon={"label"}
            label={`레이블`}
            type={"ghost"}
            size={"medium"}
            width={"50%"}
            onClick={goLabelsPage}
          />
          <Button
            icon={"milestone"}
            label={`마일스톤`}
            type={"ghost"}
            size={"medium"}
            width={"50%"}
            onClick={goMilestonesPage}
          />
        </TapButtonWrapper>
        <Button icon={"plus"} label={"마일스톤 추가"} onClick={() => {}} />
      </Tap>
    </Main>
  );
}

const Main = styled.div`
  padding: 32px 0px;
  display: flex;
  flex-direction: column;
  width: 1280px;
`;

const Tap = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
`;

const TapButtonWrapper = styled.div`
  position: relative;
  display: flex;
  width: 320px;
  height: 40px;
  border: ${({ theme }) =>
    `${theme.border.default} ${theme.colorSystem.neutral.border.default}`};
  border-radius: ${({ theme }) => theme.radius.medium};
  &::after {
    content: "";
    position: absolute;
    top: 0px;
    bottom: 0;
    left: 159px;
    width: 1px;
    background-color: ${({ theme }) =>
      theme.colorSystem.neutral.border.default};
  }
  > button {
    &:hover {
      background-color: ${({ theme }) =>
        theme.colorSystem.neutral.surface.bold};
    }
  }
  > button:first-child {
    border-top-right-radius: 0px;
    border-bottom-right-radius: 0px;
  }
  > button:last-child {
    border-top-left-radius: 0px;
    border-bottom-left-radius: 0px;
    font: ${({ theme }) => theme.font.selectedBold16};
    color: ${({ theme }) => theme.colorSystem.neutral.text.strong};
    background-color: ${({ theme }) => theme.colorSystem.neutral.surface.bold};
  }
`;
